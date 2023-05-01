import { useRouter } from "next/router"
import { RegisterRequest } from "@/types/app"
import { FormEvent, useState } from "react"
import Link from "next/link";

export default function SignUp() {
  const router = useRouter()
  const [usernameErrorMessage, setUsernameErrorMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [registerData, setRegisterData] = useState<RegisterRequest>({
    first_name: '',
    last_name: '',
    username: '',
    password: '',
    password_confirmation: ''
  })

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault()
    await register()
  }

  const handleChange = (event: any) => {
    setRegisterData({ ...registerData, [event.target.name]: event.target.value });
  };

  async function register() {
    try {
      const response = await fetch("/api/auth/register", {
        method: "POST",
        body: JSON.stringify(registerData),
      });
      const userData = await response.json()
      if (response.status == 200) {
        router.push("/events")
      }
      else {
        if(userData.message.includes("exists")){
          setUsernameErrorMessage(userData.message)
        }

        let fields = ""
        if(userData.message.includes("8 or more")){
          fields = fields.concat(fields, "Length between 8 or more")
        } else if(userData.message.includes("uppercase")){
          fields = fields.concat(fields,"At least one upper-case character")
        } else if(userData.message.includes("lowercase")){
          fields = fields.concat(fields,"At least one lower-case character")
        } else if(userData.message.includes("digit")){
          fields = fields.concat(fields,"At least one digit character")
        } else if(userData.message.includes("special")){
          fields = fields.concat(fields,"At least one special character")
        } else if(userData.message.includes("whitespace")){
          fields = fields.concat(fields,"Can not contain whitespace")
        } else if(userData.message.includes(">= 5 characters alphabetical")){
          fields = fields.concat(fields,"Can not contain a sequence of more than 4 characters alphabetical")
        } else if(userData.message.includes(">= 5 characters numerical")){
          fields = fields.concat(fields,"Can not contain a sequence of more than 4 characters numerical")
        } else if(userData.message.includes("match the original")){
          fields = fields.concat(fields,"Password confirmation doesn't match")
        }        
        if(fields !== ""){
          setErrorMessage(fields)
        }        
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
                <h1 className="font-bold text-gray-700 text-xl tracking-tight">TACS</h1>
              </div>
              <div className='mb-6'>
                <span className="font-semibold text-gray-700 text-xl tracking-tight">Sign Up</span>
              </div>
            </div>
            <div className="mb-6">
              <label className="block text-gray-700 text-sm font-bold mb-1" htmlFor="first_name">
                First Name
              </label>
              <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline mb-3"
                type="text" name="first_name" value={registerData.first_name} onChange={handleChange} required />
              <label className="block text-gray-700 text-sm font-bold mb-1" htmlFor="last_name">
                Last Name
              </label>
              <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline mb-3"
                type="text" name="last_name" value={registerData.last_name} onChange={handleChange} required />
              <label className="block text-gray-700 text-sm font-bold mb-1" htmlFor="Username">
                Username
              </label>
              <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline mb-3"
                type="text" name="username" value={registerData.username} onChange={handleChange} required />
                {usernameErrorMessage && (<p className="error text-red-500 text-xs italic mb-2"> {usernameErrorMessage} </p>)}
              <label className="block text-gray-700 text-sm font-bold mb-1" htmlFor="password">
                Password
              </label>
              <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline mb-3"
                type="password" name="password" value={registerData.password} onChange={handleChange} required />
              {errorMessage && (<p className="error text-red-500 text-xs italic mb-2"> {errorMessage} </p>)}
              <label className="block text-gray-700 text-sm font-bold mb-1" htmlFor="password_confirmation">
                Password Confirmation
              </label>
              <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline mb-3"
                type="password" name="password_confirmation" value={registerData.password_confirmation} onChange={handleChange} required />
              {errorMessage && (<p className="error text-red-500 text-xs italic mb-2"> {errorMessage} </p>)}
            </div>
            <div className="flex items-center justify-between">
              <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit">
                Sign Up
              </button>
              <Link className="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800" href="/">
                Sign In?
              </Link>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
}