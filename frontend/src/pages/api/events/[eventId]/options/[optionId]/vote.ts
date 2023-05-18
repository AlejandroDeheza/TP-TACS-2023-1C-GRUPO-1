import axios from 'axios';
import type { NextApiRequest, NextApiResponse } from 'next';
import { getCookie } from 'cookies-next';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  try {
    const jwt = getCookie('jwt', { req, res });
    const url = `${process.env.url}/v1/events/${req.query.eventId}/options/${req.query.optionId}/vote`;

    const response = await axios.patch(url, {}, {
      headers: {
        'Authorization': `Bearer ${jwt}`,
      },
    });

    res.status(response.status).json(response.data);
  } catch (error: any) {
    console.log(error);
    if (error.response) {
      res.status(error.response.status).json(error.response.data);
    } else {
      res.status(500).json({ message: 'Internal server error' });
    }
  }
}

