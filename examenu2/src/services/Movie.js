import api from "@/config/http-client.gateway"

const getNasaImg = async  () => {
    try {
        const response = await api.doGet("https://api.nasa.gov/planetary/apod?api_key=ci3TFpeygTiDlDoO003fgPW0IgSbusLcbcT3bHIM")
        return response
    } catch (error) {
        throw new Error("There was an error fetching at getPeople", error)
    }
}

export default {
    getNasaImg
}