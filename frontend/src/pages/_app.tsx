import Header from '@/components/header/header-component'
import '@/styles/globals.css'
import type { AppProps } from 'next/app'

export default function App({ Component, pageProps }: AppProps) {
  return <>

  <Component {...pageProps} />
</>
}
