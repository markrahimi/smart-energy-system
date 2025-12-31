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



export async function getUserById(id) {
  
  const res = await fetch(`${HOST}/users/${id}`)

  if (!res.ok) {
    console.log('Error getUserById: ', res)
    throw new Error('failed get user')
  }

  return res.json()
}



export async function updateUser(id, userData) {

  const res = await fetch(`${HOST}/users/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(userData)
  })

  if (!res.ok) {
    console.log('Error updateUser: ', res)
    throw new Error('failed update user')
  }

  return res.json()
}
