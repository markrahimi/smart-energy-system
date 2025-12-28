import { HOST } from '../config.js'

export async function getSensorData() {
  const res = await fetch(`${HOST}/sensor-data`)

  if (!res.ok) {
    console.log('Error getSensorData: ', res)
    throw new Error('failed get sensor data')
  }

  return res.json()
}
