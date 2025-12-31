import { HOST } from '../config.js'

export async function getUsers() {
  const res = await fetch(`${HOST}/users`)

  if (!res.ok) {
    console.log('Error getUsers: ', res)
    throw new Error('failed get users')
  }

  return res.json()
}

export async function createUser(userData) {
  const res = await fetch(`${HOST}/users`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(userData)
  })

  if (!res.ok) {
    console.log('Error createUser: ', res)
    throw new Error('failed create user')
  }

  return res.json()
}
