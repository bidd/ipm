#!/usr/bin/python
# --*-- coding: utf-8 --*--

from gi.repository import Gtk

class App():
	
	def __init__(self):
		self.builder = Gtk.Builder()
		self.builder.add_from_file("interface.glade")
		self.builder.connect_signals(self)
		w = self.builder.get_object("login_window")
		w.show_all()

	def on_login_button_clicked(self, w):
		e1 = self.builder.get_object("user_entry")
		e2 = self.builder.get_object("pass_entry")
		
		user=e1.get_text()
		passw=e2.get_text()
		
		#Capa controlador comprobar si es correcto
		verificar = 0		
		if verificar==True:
			w = self.builder.get_object("login_window")
			w.hide()
		
			w = self.builder.get_object("main_window")
			w.show_all()
		else:
			w = self.builder.get_object("failed_login_dialog")
			w.show_all()

	def on_hide_dialog(self, w):
		w = self.builder.get_object("failed_login_dialog")
		w.hide()


	def on_window_delete_event(self, w, e):
		Gtk.main_quit()

App()
Gtk.main()
