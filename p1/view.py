#!/usr/bin/python
# --*-- coding: utf-8 --*--

#Authors
#Francisco Gonzalez Vidal	
#	francisco.gonzalez
#Hector Silva Martinez
#	hector.silva

import controler
import gettext
from gi.repository import GdkPixbuf
from gi.repository import Gio 
from gi.repository import Gtk
from gi.repository import GObject
from gi.repository.GdkPixbuf import Pixbuf 
import locale
import model
import threading
import os
import urllib2


class App():
	
	def __init__(self):
		self.builder = Gtk.Builder()
		self.builder.add_from_file("interface.glade")
		self.builder.connect_signals(self)
		w = self.builder.get_object("login_window")
		w.show_all()

		self.id = None

		self.controler = controler.Controler()

	def give_feedback(self, message):
		w = self.builder.get_object("feedback_label")
		w.set_text(message)		
		w = self.builder.get_object("feedback_dialog")
		w.show_all()

##################################

	def get_image(self, url):
		try:
			response = urllib2.urlopen(url) 
			input_stream = Gio.MemoryInputStream.new_from_data(response.read(), None) 
			pixbuf = Pixbuf.new_from_stream_at_scale(input_stream, -1, 400, True, None) 
		except ValueError:
			pixbuf = None
		except :
			pixbuf = None
		return pixbuf
		

##################################

	def do_login(self, user, passwd):
		result = self.controler.login(user, passwd)
		if result:
			GObject.idle_add(self.valid_login)
		else:		
			GObject.idle_add(self.invalid_login)
		return True


	def valid_login(self):
		w = self.builder.get_object("login_process_window")
		w.hide()

		w = self.builder.get_object("login_window")
		w.hide()

		self.liststore = self.builder.get_object("liststore")
		self.controler.list_movies(self.liststore)

		text = self.builder.get_object("movie_detail")
		buff = text.get_buffer()
		string = ""
		buff.set_text(string, len(string))
		
		w = self.builder.get_object("movie_image")
		image = None
		w.set_from_pixbuf(image)

		w = self.builder.get_object("main_window")
		w.show_all()

	def invalid_login(self):
		w = self.builder.get_object("login_process_window")
		w.hide()
		
		w = self.builder.get_object("failed_login_dialog")
		w.show_all()
	
	def on_failed_login_button_clicked(self, w):
		w = self.builder.get_object("failed_login_dialog")
		w.hide()

	def on_login_button_clicked(self,w):
		e = self.builder.get_object("user_entry")
		user=e.get_text()
		e = self.builder.get_object("pass_entry")
		passwd=e.get_text()
		
		t = threading.Thread(target=self.do_login, args=(user, passwd))
		t.start()
		
		w = self.builder.get_object("login_process_window")
		w.show_all()
	
	
##################################

	
	def on_disconnect_button_clicked(self, w):
		w = self.builder.get_object("main_window")		#ocultamos la ventana principal
		w.hide()
		self.controler.set_page(1)
		
		user_entry = self.builder.get_object("user_entry")	#vaciamos los campos user y entry antes de volver a mostar login_window
		pass_entry = self.builder.get_object("pass_entry")
		user_entry.set_text("")
		pass_entry.set_text("")

		w = self.builder.get_object("login_window")		#mostramos login_window
		w.show_all()

##################################

#	Coje los datos de la pelicula id(modelo) y los mete en el dialogo correspondiente
	def on_movie_detail(self, movie_id):
		text = self.builder.get_object("movie_detail")
		buff = text.get_buffer()
		string =  self.controler.build_string(movie_id)
		buff.set_text(string, len(string))
		
		w = self.builder.get_object("movie_image")
		image = self.get_image(movie_id['url_image'])
		w.set_from_pixbuf(image)

##################################		

	def on_tree_selection_changed(self, err, err1, err2):
		treeview = self.builder.get_object("treeview")
		tree_selection = treeview.get_selection()
                (model, pathlist) = tree_selection.get_selected_rows()

        	movie_id=self.controler.get_id(model,pathlist)
		
		self.on_movie_detail(movie_id)
	
##################################

	def on_add_button_clicked(self, w):
		add_window = self.builder.get_object("add_movie_window")
		add_window.show_all()

	def do_add_movie(self, title, synopsis, image, year, category):
		result = self.controler.add(title, synopsis, image, int(year), category)
		if result:
			GObject.idle_add(self.controler.list_movies, self.liststore)
			GObject.idle_add(self.valid_add_movie)
		else:		
			GObject.idle_add(self.invalid_add_movie)
		return True

	def valid_add_movie(self):
		self.give_feedback("The movie has been added")

	def invalid_add_movie(self):
		self.give_feedback("Error: the movie has NOT been added")
	
	def on_add_movie_button_clicked(self, w):
		title = self.builder.get_object("title_entry")
		title = title.get_text()
		synopsis = self.builder.get_object("synopsis_entry")
		synopsis = synopsis.get_buffer()		
		synopsis = synopsis.get_text( synopsis.get_start_iter(), synopsis.get_end_iter(), 0)
		image = self.builder.get_object("image_entry")
		image = image.get_text()		
		year = self.builder.get_object("year_entry")
		year = year.get_text()
		category = self.builder.get_object("category_entry")
		category = category.get_text()

		t = threading.Thread(target=self.do_add_movie, args=(title, synopsis, image, int(year), category))
		t.start()

		add_window = self.builder.get_object("add_movie_window")	
		add_window.hide()

##################################

	def do_delete(self, ids):
		result = self.controler.delete(ids)
		if result:
			GObject.idle_add(self.controler.list_movies, self.liststore)
			GObject.idle_add(self.valid_delete)
		else:		
			GObject.idle_add(self.invalid_delete)
		return True

	def valid_delete(self):
		self.give_feedback("The movie has been deleted")
	
	def invalid_delete(self):
		self.give_feedback("Error: the movie has NOT been deleted")
	
	def on_delete_button_clicked(self, w):
		data = self.controler.get_data()
		try:		
			ids = data['id']
			t = threading.Thread(target=self.do_delete, args=(ids,))
			t.start()
		except:
			self.give_feedback("Error: no movie has been selected")		

##################################

	def on_edit_button_clicked(self, w):
		try:
			edit_window = self.builder.get_object("edit_movie_window")
			data = self.controler.get_data()
		
			title = self.builder.get_object("title_entry1")
			title = title.set_text(data['title'])

			synopsis = self.builder.get_object("synopsis_entry1")
			synopsis = synopsis.get_buffer()		
			synopsis = synopsis.set_text( data['synopsis'], len(data['synopsis']) )

			image = self.builder.get_object("image_entry1")
			image = image.set_text(data['url_image'])		

			year = self.builder.get_object("year_entry1")
			year = year.set_text(str(data['year']))

			category = self.builder.get_object("category_entry1")
			category = category.set_text(data['category'])
						
			edit_window.show_all()
		except:
			self.give_feedback("Error: no movie has been selected")

	def do_edit(self, ids, title, synopsis, image, year, category):
		result = self.controler.edit(ids, title, synopsis, image, int(year), category)
		if result:
			GObject.idle_add(self.controler.list_movies, self.liststore)
			GObject.idle_add(self.valid_edit)
		else:		
			GObject.idle_add(self.invalid_edit)
		return True

	def valid_edit(self):
		self.give_feedback("The movie has been edited")

	def invalid_edit(self):
		self.give_feedback("Error: the movie has NOT been edited")

	def on_edit_movie_button_clicked(self, w):
		title = self.builder.get_object("title_entry1")
		title = title.get_text()
		
		synopsis = self.builder.get_object("synopsis_entry1")
		synopsis = synopsis.get_buffer()		
		synopsis = synopsis.get_text( synopsis.get_start_iter(), synopsis.get_end_iter(), 0)
		
		image = self.builder.get_object("image_entry1")
		image = image.get_text()		
		
		year = self.builder.get_object("year_entry1")
		year = year.get_text()
		
		category = self.builder.get_object("category_entry1")
		category = category.get_text()	
		
		data = self.controler.get_data()

		ids = data['id']		
		t = threading.Thread( target=self.do_edit, args=(ids, title, synopsis, image, int(year), category) )
		t.start()
		
		edit_window = self.builder.get_object("edit_movie_window")	
		edit_window.hide()


##################################

	def on_previous_button_clicked(self, w):
		self.controler.previous_page(self.liststore)
	
	def on_next_button_clicked(self,w):
		self.controler.next_page(self.liststore)

##################################

	def on_hide_dialog(self, w):
		w = self.builder.get_object("failed_login_dialog")
		w.hide()

	def on_ok_button_clicked(self,w):
		w = self.builder.get_object("feedback_dialog")
		w.hide()

	def on_add_movie_window_delete_event(self, w, e):
		add_movie_window = self.builder.get_object("add_movie_window")
		add_movie_window.hide()
		return True	

	def on_edit_movie_window_delete_event(self, w, e):
		edit_movie_window = self.builder.get_object("edit_movie_window")
		edit_movie_window.hide()
		return True	

	def on_window_close(self, w):
		Gtk.main_quit()

	def on_window_delete_event(self, w, e):
		Gtk.main_quit()

App()
Gtk.main()	
