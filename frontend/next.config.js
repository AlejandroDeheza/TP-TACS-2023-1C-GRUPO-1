/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
}

const isDev = process.env.NODE_ENV === 'development'

module.exports = {
  env: {
    domain: isDev ? 'localhost':'backend',
  }
}
