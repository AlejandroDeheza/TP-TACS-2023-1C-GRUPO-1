import type { NextApiRequest, NextApiResponse } from 'next';
import { setCookie } from 'cookies-next';
import axios from 'axios';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  axios.get("http://localhost:8091/v1/auth/refresh-token" ,{
  }).then((response) => {
    setCookie('jwt', response.data.access_token, { req, res, maxAge: 60 * 60 * 24 });
    res.status(response.status).json(response.data)
  }).catch((error) => {
    res.status(error.response.status).json(error.response.data)
  })
}  