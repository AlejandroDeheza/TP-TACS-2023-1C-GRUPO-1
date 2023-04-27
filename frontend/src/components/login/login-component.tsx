import React, { useState, FormEvent } from 'react';
import { useRouter } from 'next/router';
import { setCookie } from 'nookies'

export default function LoginComponent(req: any, res: any){
  const router = useRouter();
  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault()
    await authenticate({ "username": username, "password": password })
  }

  async function authenticate(data: object) {
    try {
      const response = await fetch("/api/auth/authentication", {
        method: "POST",
        body: JSON.stringify(data),
      });

      const userData = await response.json()
      console.log(userData)

      setCookie({ res }, 'jwt', userData.access_token, {
        httpOnly: false,
        secure: process.env.NODE_ENV !== 'development',
        maxAge: 30 * 24 * 60 * 60,
        path: '/',
      });

    } catch (error) {
      console.log(error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label htmlFor="username">Username </label>
      <input type="text" id="username" name="username" value={username} onChange={e => setUsername(e.target.value)} required />

      <label htmlFor="password">Password </label>
      <input type="password" id="password" name="password" value={password} onChange={e => setPassword(e.target.value)} required />

      <button type="submit">Login</button>
    </form>
  );
};