import { HOST } from '../config.js'



export async function getUsers() {
    
  const res = await fetch(`${HOST}/users`)

  if(!res.ok) {
    console.log('Error getUsers: ',res)
    throw new Error('failed get users')
  }

  return res.json()
}

