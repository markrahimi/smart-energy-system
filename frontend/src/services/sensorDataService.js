import { HOST } from '../config.js'

export async function getSensorData() {
  const res = await fetch(`${HOST}/sensor-data`)

  if (!res.ok) {
    console.log('Error getSensorData: ', res)
    throw new Error('failed get sensor data')
  }

  return res.json()
}




export async function createDevice(deviceData){
  
  const res= await fetch(`${HOST}/devices`,{
    method:'POST',
    headers:{
      'Content-Type':'application/json'
    },
    body: JSON.stringify(deviceData)
  })

  if(!res.ok){
    console.log('Error createDevice: ',res)
    throw new Error('failed create device')
  }

  return res.json()
}
