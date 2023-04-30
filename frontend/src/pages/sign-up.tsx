import Header from "../components/header/header-component"
import Footer from "../components/footer/footer-component"
import { useRouter } from "next/router"
import { RegisterRequest } from "@/types/app"
import { FormEvent, useState } from "react"

export default function SignUp() {
    const router = useRouter()
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
        console.log(userData)
        if(response.status == 200){
          router.push("/home")
        }
        else{
          alert(userData.message)
        }
  
      } catch (error) {
        console.log(error);
      }
    };

    return (
        <main>
            <Header />
            <form onSubmit={handleSubmit}>
                <label htmlFor="first_name">First Name </label>
                <input type="first_name" name="first_name" value={registerData?.first_name} onChange={handleChange} required />

                <label htmlFor="last_name">Last Name </label>
                <input type="text" name="last_name" value={registerData?.last_name} onChange={handleChange} required />

                <label htmlFor="username">Username </label>
                <input type="text" name="username" value={registerData?.username} onChange={handleChange} required />

                <label htmlFor="password">Password </label>
                <input type="password" name="password" value={registerData?.password} onChange={handleChange} required />

                <label htmlFor="password_confirmation">Confirmation Password </label>
                <input type="password" name="password_confirmation" value={registerData?.password_confirmation} onChange={handleChange} required />

                <button type="submit">Sign Up</button>
            </form>
            <Footer />
        </main>
    );
}