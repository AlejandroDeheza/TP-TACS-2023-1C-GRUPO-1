import type { NextApiRequest, NextApiResponse } from 'next';
import { setCookie } from 'cookies-next';
import axios from 'axios';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  axios.post(`${process.env.scheme}${process.env.domain}:8091/v1/auth/logout`).then((response) => {
    res.status(response.status).json(response.data)
  }).catch((error) => {
    res.status(error.response.status).json(error.response.data)
  })
}  