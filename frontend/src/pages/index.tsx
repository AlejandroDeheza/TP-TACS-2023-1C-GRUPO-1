import React, { useState, FormEvent, useRef } from 'react';
import { AuthenticationRequest } from '../types/app'
import { useRouter } from "next/router";
import error from 'next/error';


export default function Login(req: any, res: any) {
  const router = useRouter()
  const [errorMessage, setErrorMessage] = useState();
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
        setErrorMessage(userData.message)
      }

    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className='flex flex-col justify-center min-h-screen'>
      <main className="flex flex-col items-center justify-center">
        <div className="w-full max-w-xs">
          <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4" onSubmit={handleSubmit}>
            <div className="mb-4">
              <div className='mb-6'>
                <span className="font-semibold text-gray-700 text-xl tracking-tight">Login</span>
              </div>
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
              {errorMessage && (<p className="error text-red-500 text-xs italic"> {errorMessage} </p>)}
            </div>
            <div className="flex items-center justify-between">
              <button className="bg-teal-500 hover:bg-teal-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit">
                Sign In
              </button>
              <a className="inline-block align-baseline font-bold text-sm text-teal-500 hover:text-teal-800" href="#">
                Sign Up?
              </a>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};