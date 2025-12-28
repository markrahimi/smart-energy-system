import { HOST } from '../config.js'



export async function getDevices(){

  const res = await fetch(`${HOST}/devices`)

  if (!res.ok){
    console.error('Error getDevices:', res)
    throw new Error('failed get devices')
  }

  return res.json()
}
