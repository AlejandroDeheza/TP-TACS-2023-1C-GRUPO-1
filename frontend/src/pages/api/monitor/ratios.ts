import type { NextApiRequest, NextApiResponse } from 'next';
import { getCookie } from 'cookies-next';
import axios from 'axios';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const jwt = getCookie('jwt', { req, res })

  axios.get(`${process.env.scheme}${process.env.domain}:8091/v1/monitor/ratios`, {
    headers: {
      'Authorization': `Bearer ${jwt}`,
    }
  }).then((response) => {
    res.status(response.status).json(response.data)
  }).catch((error) => {
    res.status(error.response.status).json(error.response.data)
  })
}
