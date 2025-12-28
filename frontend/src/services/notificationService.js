import { HOST } from '../config.js'

export async function getNotifications() {
  const res = await fetch(`${HOST}/notifications`)

  if (!res.ok) {
    console.log('Error getNotifications: ', res)
    throw new Error('failed get notifications')
  }

  return res.json()
}
