#!/usr/bin/python
# --*-- coding: utf-8 --*--
import requests
import json
import time

class Model():
	
	def __init__(self):
		self.cookie=None
		self.lista=[]
		self.page=1
	
	def log(self,user,passw):
#		time.sleep(3)
		payload = {'username': user, 'passwd': passw}
		session = requests.post('http://ipm-movie-database.herokuapp.com/login',params=payload)
		json = session.json()
		self.cookie=session.cookies
		if json["result"]=="success":
			return True
		else:	
			return False
	
	def next_page(self):
		self.page +=1

	def previous_page(self):
		if (self.page > 1):
			self.page -=1
		return self.page >= 1
	
	def set_page(self, page):
		self.page = page
	
	def list_movies(self):
		movies = requests.get('http://ipm-movie-database.herokuapp.com/movies/page/'+str(self.page))
		if movies.json()['result']=='success':		
			self.lista=[]
			r=movies.json()
			for i in range(len(r['data'])):
				a = r['data'][i]
				b = a.values()
				self.lista= self.lista+[b]
			return self.lista
		else:
			self.page -= 1
			return self.lista
	
	def add_movie(self, title, synopsis, image, year, category):
		film={"title":title,"synopsis":synopsis,"category":category,"year":year,"url_image":image}
		r = requests.post('http://ipm-movie-database.herokuapp.com/movies',data=film,cookies=self.cookie)
		json = r.json()
		if json["result"]=="success":
			return True
		else:	
			return False

	def delete(self, ids):
		ids=str(ids)
		r = requests.delete('http://ipm-movie-database.herokuapp.com/movies/'+ids, cookies=self.cookie)
		json = r.json()
		if json["result"]=="success":
			return True
		else:	
			return False
		
	def search(self, ids):
		ids=str(ids)
		r = requests.get('http://ipm-movie-database.herokuapp.com/movies/'+ids)
		json = r.json()
		return json['data']

	def edit(self, ids, title, synopsis, image, year, category):
		film={"title":title,"synopsis":synopsis,"category":category,"year":year,"url_image":image}
		r = requests.put('http://ipm-movie-database.herokuapp.com/movies/'+str(ids),data=film,cookies=self.cookie)
		json = r.json()
		if json["result"]=="success":
			return True
		else:	
			return False
