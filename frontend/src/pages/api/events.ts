import nookies from 'nookies';

export default async function handler(req: any, res: any) {
  const cookies = nookies.get(res)
  const response = await fetch("http://localhost:8091/v1/events", {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${cookies.jwt}`,
    }
  })

  const data = await response.json()
  res.status(200).json(data);
}


