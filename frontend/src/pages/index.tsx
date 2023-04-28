import React, { useState, FormEvent } from 'react';
import { AuthenticationRequest } from '../types/app'
import { useRouter } from "next/router";
import Header from "../components/header/header-component"
import Footer from "../components/footer/footer-component"

export default function Login(req: any, res: any) {
  const router = useRouter()
  const [authenticationData, setAuthenticationData] = useState<AuthenticationRequest>({
    username: "",
    password: ""
  })

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault()
    await authenticate()
  }

  const handleChange = (event: any) => {
    setAuthenticationData({ ...authenticationData, [event.target.name]: event.target.value });
  };

  async function authenticate() {
    try {
      const response = await fetch("/api/auth/authentication", {
        method: "POST",
        body: JSON.stringify(authenticationData),
      });
      const userData = await response.json()
      console.log(userData)
      if (response.status == 200) {
        router.push("/home")
      }
      else {
        alert(userData.message)
      }

    } catch (error) {
      console.log(error);
    }
  };

  return (
    <main>
      <Header />
      <div className="w-full max-w-xs">
        <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4" onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="username">
              Username
            </label>
            <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" 
            type="text" name="username" value={authenticationData.username} onChange={handleChange} required />
          </div>
          <div className="mb-6">
            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="password">
              Password
            </label>
            <input className="shadow appearance-none border border-red-500 rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline" 
            type="password" name="password" value={authenticationData.password} onChange={handleChange} required />
          </div>
          <div className="flex items-center justify-between">
            <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit">
              Sign In
            </button>
            <a className="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800" href="#">
              Sign Up?
            </a>
          </div>
        </form>
      </div>
      <Footer />
    </main>

  );
};