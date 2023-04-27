import axios from 'axios';
import type { NextApiRequest, NextApiResponse } from 'next';
import { getCookie } from 'cookies-next';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const jwt = getCookie('jwt', { req, res })
  const url = "http://backend:8091/v1/events"
  if (req.method == "GET") {
    axios.get(url, {
      headers: {
        'Authorization': `Bearer ${jwt}`,
      }
    }).then((response) => {
      res.status(response.status).json(response.data)
    }).catch((error) => {
      res.status(error.response.status).json(error.response.data)
    })
  }
  else {
    axios.post(url, req.body, {
      headers: {
        'Authorization': `Bearer ${jwt}`,
      }
    }).then((response) => {
      res.status(response.status).json(response.data)
    }).catch((error) => {
      res.status(error.response.status).json(error.response.data)
    })
  }
}
