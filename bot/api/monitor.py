import os

import requests

import user
from env import SCHEME_DOMAIN

def get_events_marketing_report() -> dict:
    url = f'{SCHEME_DOMAIN}/v1/monitor/ratios'
    headers = {"Authorization": f'Bearer {user.User.token}'}
    response = requests.get(url, headers=headers)
    json = response.json()
    result = json if response.status_code == 200 else json['message']
    return result


def get_options_report() -> dict:
    url = f'{SCHEME_DOMAIN}/v1/monitor/options'
    headers = {"Authorization": f'Bearer {user.User.token}'}
    response = requests.get(url, headers=headers)
    json = response.json()
    if response.status_code == 200:
        if not json:
            result = "There's no options voted"
        else:
            result = json
    else:
        result = json['message']
    return result
