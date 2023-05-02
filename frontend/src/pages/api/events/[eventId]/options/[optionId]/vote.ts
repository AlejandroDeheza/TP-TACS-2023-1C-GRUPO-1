import axios from 'axios';
import type { NextApiRequest, NextApiResponse } from 'next';
import { getCookie } from 'cookies-next';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const jwt = getCookie('jwt', { req, res })
  const url = `http://${process.env.domain}:8091/v1/events/${req.query.eventId}/options/${req.query.optionId}/vote`
  axios.patch(url, {}, {
    headers: {
      'Authorization': `Bearer ${jwt}`,
    }
  }).then((response) => {
    res.status(response.status).json(response.data)
  }).catch((error) => {
    console.log(error)
    res.status(error.response.status).json(error.response.data)
  })
}
