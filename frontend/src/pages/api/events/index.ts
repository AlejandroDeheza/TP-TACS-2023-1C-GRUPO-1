import axios from 'axios';
import type { NextApiRequest, NextApiResponse } from 'next';
import { getCookie } from 'cookies-next';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const jwt = getCookie('jwt', { req, res })

  axios.get("http://localhost:8091/v1/events", {
    headers: {
      'Authorization': `Bearer ${jwt}`,
    }
  }).then((response) => {
    res.status(response.status).json(response.data)
  }).catch((error) => {
    res.status(error.response.status).json(error.response.data)
  })
}
