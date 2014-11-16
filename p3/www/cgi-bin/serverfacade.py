#!/usr/bin/env python
# -*- coding: utf-8 -*-

import requests


#SERVER = "http://localhost:5000"
SERVER = "http://ipm-movie-database.herokuapp.com"


class Auth(object):

	def __init__(self):
		self.cookies = None

	def get_cookie(self):
		if self.cookies:
			return self.cookies['rack.session']
		else:
			return None

	def set_cookie(self, cookie_string):
		self.cookies = requests.cookies.cookielib.CookieJar()
		cookie = requests.cookies.create_cookie('rack.session', cookie_string)
		self.cookies.set_cookie(cookie)

	def login(self, login_params):
		try:
			data = requests.post(SERVER + "/login", params=login_params)
			self.cookies = data.cookies
			return data.json()
		except requests.exceptions.ConnectionError:
			return None

	def listMovie(self, page):
		try:
			movies = requests.get('http://ipm-movie-database.herokuapp.com/movies/page/'+str(page))
			lista = []
			if movies.json()['result']=='success':		
				r=movies.json()
				data = r['data']
				for movie in data:
					element = (movie['title'].encode('utf-8'), movie['year'], movie['id'])
					lista.append(element)
				return lista
			else:
				return None
		except requests.exceptions.ConnectionError:
			return None

	def is_last_page(self, page):
		try:
			movies = requests.get('http://ipm-movie-database.herokuapp.com/movies/page/'+str(page))
			if movies.json()['result']=='success':		
				return False
			else:
				return True
		except requests.exceptions.ConnectionError:
			return null  

	def movie_detail(self, ids):
		try:
			w_id=str(ids)
			r = requests.get('http://ipm-movie-database.herokuapp.com/movies/'+w_id)
			json = r.json()
			return json['data']
		except requests.exceptions.ConnectionError:
			return null    	

	def search_movie(self, query):
		try:
			response = requests.get('http://ipm-movie-database.herokuapp.com/movies?q='+query)
			return response.json()
		except requests.exceptions.ConnectionError:
			return null

	def session(self, cookie_string):
		try:
			self.set_cookie(cookie_string)
			data = requests.get(SERVER + "/session", cookies=self.cookies)
			return data.json()
		except requests.exceptions.ConnectionError:
			return None
	
	def add_movie(self, movie):
		r = requests.post('http://ipm-movie-database.herokuapp.com/movies',data=movie,cookies=self.cookies)
		json = r.json()
		return json["result"]=="success"
			
	def is_fav(self, ids):
		r = requests.get('http://ipm-movie-database.herokuapp.com/movies/'+ids+'/fav', cookies=self.cookies)
		json = r.json()
		return json["data"] == "true"
		
	def fav_movie(self, ids):
		r = requests.post('http://ipm-movie-database.herokuapp.com/movies/'+ids+'/fav', cookies=self.cookies)		
	
	def unfav_movie(self, ids):
		r = requests.delete('http://ipm-movie-database.herokuapp.com/movies/'+ids+'/fav', cookies=self.cookies)
	
	def delete_movie(self, ids):
		r = requests.delete('http://ipm-movie-database.herokuapp.com/movies/'+ids, cookies=self.cookies)
		return r.json()
		