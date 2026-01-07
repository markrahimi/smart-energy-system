import { HOST } from '../config.js'

export async function getThresholds(userId) {
  const url = userId ? `${HOST}/thresholds?userId=${userId}` : `${HOST}/thresholds`
  const res = await fetch(url)

  if (!res.ok) {
    console.error('Error getThresholds:', res)
    throw new Error('failed to get thresholds')
  }

  return res.json()
}

export async function getThresholdByDeviceId(deviceId) {
  const res = await fetch(`${HOST}/thresholds/device/${deviceId}`)

  if (!res.ok) {
    console.error('Error getThresholdByDeviceId:', res)
    throw new Error('failed to get threshold')
  }

  return res.json()
}

export async function createThreshold(thresholdData) {
  const res = await fetch(`${HOST}/thresholds`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(thresholdData)
  })

  if (!res.ok) {
    console.error('Error createThreshold:', res)
    throw new Error('failed to create threshold')
  }

  return res.json()
}

export async function updateThreshold(deviceId, thresholdData) {
  const res = await fetch(`${HOST}/thresholds/device/${deviceId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(thresholdData)
  })

  if (!res.ok) {
    console.error('Error updateThreshold:', res)
    throw new Error('failed to update threshold')
  }

  return res.json()
}

export async function deleteThreshold(deviceId) {
  const res = await fetch(`${HOST}/thresholds/device/${deviceId}`, {
    method: 'DELETE'
  })

  if (!res.ok) {
    console.error('Error deleteThreshold:', res)
    throw new Error('failed to delete threshold')
  }
}
