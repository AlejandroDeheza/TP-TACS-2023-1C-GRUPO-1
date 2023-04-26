import { useRouter } from 'next/router';
import axios from 'axios';
import nookies from 'nookies';
import LoginComponent from '../components/login/login-component';

const Home = () => {
  const router = useRouter();
  return (
    <div>
      <LoginComponent />
    </div>
  )
}

export default Home;
