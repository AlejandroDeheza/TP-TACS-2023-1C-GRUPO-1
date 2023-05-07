import requests
from telebot import types

import user


def authenticate(message: types.Message, password: str) -> dict:
    url = "http://localhost:8091/v1/auth/authentication"
    body = {"username": message.from_user.id, "password": password}
    response = requests.post(url, json=body)
    json = response.json()
    if response.status_code == 200:
        user.User.token = json['access_token']
    result = "Login successfully." if response.status_code == 200 else json['message']
    return result


def register(message: types.Message, password: str) -> dict:
    url = "http://localhost:8091/v1/auth/register"
    body = {"first_name": message.from_user.first_name, "last_name": message.from_user.last_name,
            "username": message.from_user.id, "password": password, "password_confirmation": password}
    response = requests.post(url, json=body)
    json = response.json()
    if response.status_code == 200:
        user.User.token = json['access_token']
    result = "Sign up successfully." if response.status_code == 200 else json['message']
    return result
