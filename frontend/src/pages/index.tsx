import React, { useState, FormEvent } from 'react';
import { AuthenticationRequest } from '../types/app'
import { useRouter } from "next/router";

export default function Login(req: any, res: any){
  const router = useRouter()  
  const [username, getUsername] = useState("")
  const [password, getPassword] = useState("")

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
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label htmlFor="username">Username </label>
      <input type="text" name="username" value={username} onChange={e => getUsername(e.target.value)} required />

      <label htmlFor="password">Password </label>
      <input type="password" name="password" value={password} onChange={e => getPassword(e.target.value)} required />

      <button type="submit" onClick={() => router.push("/home")}>Login</button>
    </form>
  );
};