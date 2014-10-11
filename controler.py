#!/usr/bin/python
# --*-- coding: utf-8 --*--

import requests
import json
import model
import threading
from gi.repository import GObject

class Controler():
	
	def __init__(self):
		self.model=model.Model()
		self.b = None

##################################
	
	def return_log(self, json):
		return json

	def login(self, user, passwd):
		#user="francisco.gonzalez"
		#passwd="vt0KY9yn"
		return self.model.log( user, passwd)


##################################

#2	Esta es la funcion que se ejecuta en el thread separado del main thread
	def do_list_movies(self, liststore):
		lista= self.model.list_movies()					#Llama a modelo para que haga sus cosas con la base de datos
		GObject.idle_add(self.return_list_movies, liststore, lista)	#Le dice al main thread que cuando este idle llame a la funcion self.return_list_movies con los argumentos que esta al lado, esta es la linea que hace que los datos de este thread se pasen al main thread para que gtk pueda trabajar con ellos
		return True								#Se cierra el thread

#3	Esta funcion vuelve a estar en el main thread y hace exactamente lo que se hacia sin threads una vez se tenian los datos
	def return_list_movies(self, liststore, lista):
		liststore.clear()						
		for i in range(len(lista)):
			movies_iter = liststore.append(lista[i])

#1	Este es el que se lanza primero, es el que se llama desde app.py
	def list_movies(self,liststore):
		t = threading.Thread(target=self.do_list_movies, args=(liststore,))	#Añade a un thread una llamada a la funcion self.do_list_movies con los argumentos que vienen al lado (la coma no hace nada pero sin ella no me iba)
		t.start()								#Lanza el thread que se creo en la linea anterior

##################################

	def add(self, title, synopsis, image, year, category):
		return self.model.add_movie(title, synopsis, image, year, category)

##################################

	def delete(self, ids):
		return self.model.delete(ids)

##################################
	
	def edit(self, ids,  title, synopsis, image, year, category):
		return self.model.edit(ids, title, synopsis, image, year, category)

##################################

	def next_page(self, liststore):
		self.model.next_page()
		self.list_movies(liststore)

	def previous_page(self, liststore):
		if self.model.previous_page():
			self.list_movies(liststore)

	def set_page(self, page):
		self.model.set_page(page)

##################################

	def get_id(self,model,pathlist):
		for path in pathlist :
                	tree_iter = model.get_iter(path)
      		        value = model.get_value(tree_iter,2)
		self.b=self.model.search(value) 
		return self.b

	def build_string(self,lista):
		string = "Titulo \n" + lista['title']+"\n\nSinopsis \n"+lista['synopsis']+"\n\nAnno \n"+str(lista['year'])+"\n\ncategoria \n"+lista['category']+"\n\nusuario \n"+lista['username']
		return string
			
	def get_data(self):
		return self.b
