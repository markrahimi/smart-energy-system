import { HOST } from '../config.js'

export async function getDevices() {
  const res = await fetch(`${HOST}/devices`)

  if (!res.ok) {
    console.error('Error getDevices:', res)
    throw new Error('failed get devices')
  }

  return res.json()
}



export async function createDevice(deviceData) {
  
  const res = await fetch(`${HOST}/devices`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(deviceData)
  })

  if (!res.ok) {
    console.log('Error createDevice: ', res)
    throw new Error('failed create device')
  }

  return res.json()
}