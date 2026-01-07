import { HOST } from '../config.js'

export async function getSensorData() {
  const res = await fetch(`${HOST}/sensor-data`)

  if (!res.ok) {
    console.log('Error getSensorData: ', res)
    throw new Error('failed get sensor data')
  }

  return res.json()
}



export async function createSensorData(sensorData) {
  const res = await fetch(`${HOST}/sensor-data`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(sensorData)
  })

  if (!res.ok) {
    console.log('Error createSensorData: ', res)
    throw new Error('failed to create sensor data')
  }

  return res.json()
}
