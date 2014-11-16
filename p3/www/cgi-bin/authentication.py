#!/usr/bin/env python
# -*- coding: utf-8 -*-
import cgi
import Cookie
import os
from serverfacade import Auth
import template_movies
import template_login
import template_detail


# Parse request params
def get_params():
	form = cgi.FieldStorage()
	action = None
	params = None
	page = None
	ids = None
	query = None
	movie = None
	if form.has_key("action"):
		action = form["action"].value
		if action == "add_movie":
			movie = ({'title':form['title'].value,
					'synopsis':form['synopsis'].value,
					'category':form['category'].value,
					'year':form['year'].value,
					'url_image':form['image'].value})
	if form.has_key("username") and form.has_key("passwd"):
		params = {"username":form["username"].value, "passwd":form["passwd"].value}
	if form.has_key("page"):
		page = form["page"].value
	if form.has_key("ids"):
		ids = form["ids"].value
	if form.has_key("query"):
		query = form["query"].value
	return action, params, page, ids, query, movie

# Creates a new cookie from a string
def create_cookie(cookie_string):
	cookie = None
	if cookie_string:
		cookie = Cookie.SimpleCookie()
		cookie['ipm-mdb'] = cookie_string
		cookie['ipm-mdb']['expires'] = 24 * 60 * 60
	return cookie

def do_session(cookie_string):
	auth = Auth()
	response = auth.session(cookie_string)
	if response:
		return response
	else:
		return "{'error': 'connection error'}"

def list_movies(page):
	movies_list = ""
	auth = Auth()
	lista = auth.listMovie(page)
	for i in range(len(lista)):        
		movies_list = (movies_list + "<tr><td> <a href= authentication.py?action=detail&ids=" 
						+ str(lista[i][2]) + ">" + lista[i][0] + "</a></td><td>" + str(lista[i][1]) + "</td></tr>")
	return movies_list

def check_login(response):
	return response["result"] == "success"

def do_login(params):
	auth = Auth()
	response = auth.login(params)
	show_message="<p class='lead warning'>Incorrect username or password</p>"
	if response:
		new_cookie = create_cookie(auth.get_cookie())	
		if check_login(response):
			print new_cookie
			do_movies('1')
		else:
			print new_cookie
			print 'Content-Type: text/html\n\n'
			if params == {'username':'u','passwd':'p'}:
				show_message = ''
			print template_login.html_login.format(message=show_message)
	else:
		print "Error: connection error"

def do_logout():
	do_login({'username':'u','passwd':'p'})

def do_movies(page):
	w_previous_page = int(page) - 1
	w_next_page = int(page) + 1
	if page == '1':
		w_previous_page = 1 
	auth = Auth()
	if auth.is_last_page(int(page)+1):
		w_next_page = int(page)
	print 'Content-Type: text/html\n\n'
	print template_movies.html_movies.format(movies=list_movies(page), previous_page=str(w_previous_page), next_page=str(w_next_page))

def do_detail(ids, cookie_string):
	auth = Auth()
	auth.set_cookie(cookie_string[8:])
	response = auth.movie_detail(ids)
	is_fav = auth.is_fav(ids)
	if is_fav:
		fav_image = "../i/heart.png"
	else:
		fav_image = "../i/heart_empty.png"
	print 'Content-Type: text/html\n\n'
	print template_detail.html_detail.format(message = '',
											ids = response['id'],
											fav = fav_image,
											image = response['url_image'].encode('utf-8'),
											title = response['title'].encode('utf-8'),
											year = response['year'],
											category = response['category'].encode('utf-8'),
											synopsis = response['synopsis'].encode('utf-8'))

def do_search(query):
	auth = Auth()
	response = auth.search_movie(query)
	movies = response['data']
	movies_list=""
	for i in range(len(movies)):        
		movies_list = (movies_list + "<tr><td> <a href= authentication.py?action=detail&ids=" 
        				+ str(movies[i]['id']) + ">" + movies[i]['title'].encode('utf-8') + "</a></td><td>" + str(movies[i]['year']) + "</td></tr>")
	if len(movies) == 0:
		movies_list = "<tr><td> No movies found </td></tr>"
	print 'Content-Type: text/html\n\n'
	print template_movies.html_movies.format(movies=movies_list, previous_page='1', next_page='1')
	
def do_add_movie(movie, cookie_string):
	auth = Auth()
	auth.set_cookie(cookie_string[8:]);
	response = auth.add_movie(movie)
	do_movies(1)
		
def do_change_fav(ids, cookie_string):
	auth = Auth()
	auth.set_cookie(cookie_string[8:])
	is_fav = auth.is_fav(ids)
 	if is_fav:
 		auth.unfav_movie(ids)
 	else:
 		auth.fav_movie(ids)
 	do_detail(ids, cookie_string)
 	
def do_delete(ids, cookie_string):
	auth = Auth()
	auth.set_cookie(cookie_string[8:])
	response = auth.delete_movie(ids)
	if response['result'] == 'success':
		do_movies(1)
	else:
		do_detail(ids, cookie_string)


def main(cookie_string):
	action, params, page, ids, query, movie = get_params()
	if action:
		if action == "login": # login returns a new cookie
			do_login(params)
		if action == "logout":
			do_logout()
		if action == "movies":
			do_movies(page)
		if action == "detail":
			response = do_detail(ids, cookie_string)
		if action == "search":
			do_search(query)
		if action == "add_movie":
			do_add_movie(movie, cookie_string)
		if action == "fav":
			do_change_fav(ids, cookie_string)
		if action == "delete":
			do_delete(ids, cookie_string)
	else:
		auth = Auth()
		response = auth.session(cookie_string[8:])
		if check_login(response):
			do_movies(1)
		else:
			print 'Content-Type: text/html\n\n'
			print template_login.html_login.format(message="")
	return True

try:
	cookie_string = os.environ.get('HTTP_COOKIE') # get the cookie (the cookie is a lie)
	main(cookie_string)
except:
	cgi.print_exception()