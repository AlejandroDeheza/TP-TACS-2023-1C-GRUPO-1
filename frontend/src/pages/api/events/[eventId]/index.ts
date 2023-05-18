import type { NextApiRequest, NextApiResponse } from 'next';
import { getCookie } from 'cookies-next';
import axios from 'axios';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
    const jwt = getCookie('jwt', { req, res });

    try {
        let response;
        if (req.method === 'GET') {
            const url = `${process.env.path}/v1/events/${req.query.eventId}`;
            response = await axios.get(url, {
                headers: {
                    'Authorization': `Bearer ${jwt}`,
                },
            });
        } else {
            const url = `${process.env.path}/v1/events/${req.query.eventId}?status=${req.query.status}`;
            response = await axios.patch(url, {}, {
                headers: {
                    'Authorization': `Bearer ${jwt}`,
                },
            });
        }

        res.status(response.status).json(response.data);
    } catch (error: any) {
        if (error.response) {
            res.status(error.response.status).json(error.response.data);
        } else {
            res.status(500).json({ message: 'Internal server error' });
        }
    }
}
