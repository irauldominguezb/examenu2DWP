import api from "@/config/http-client.gateway"

const saveBook = async  (book) => {
    try {
        const response = await api.doPost("book/", book)
        return response
    } catch (error) {
        throw new Error("There was an error fetching at saveBook", error)
    }
}

const getBook = async (pagination = {}) => {
    try {
        const response = await api.doGet("book/", pagination)
        return response.data.content
    } catch (error) {
        throw new Error("There was an error getting at getBook", error)
    }
}

const deleteBook = async (id) => {
    try {
        const response = await api.doDelete(`book/${id}`)
        return response
    } catch (error) {
        throw new Error("There was an error deleting at deletingBook", error)
    }

}

const update = async (book) => {
    try {
        const response = await api.doPut(`book/${+book.id}`, book)
        return response
    } catch (error) {
        throw new Error("There was an error deleting at deletingBook", error)
    }

}


export default {
    saveBook,
    getBook,
    update,
    deleteBook
}