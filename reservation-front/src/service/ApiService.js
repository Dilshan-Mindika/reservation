import axios from "axios"

export default class ApiService {

    static BASE_URL = "http://localhost:4040"

    static getHeader() {
        const token = localStorage.getItem("token");
        return {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        };
    }

    /**AUTH */

    /* This  register a new user */
    static async registerUser(registration) {
        const response = await axios.post(`${this.BASE_URL}/auth/register`, registration)
        return response.data
    }

    /* This  login a registered user */
    static async loginUser(loginDetails) {
        const response = await axios.post(`${this.BASE_URL}/auth/login`, loginDetails)
        return response.data
    }

    /***USERS */


    /*  This is  to get the user profile */
    static async getAllUsers() {
        const response = await axios.get(`${this.BASE_URL}/users/all`, {
            headers: this.getHeader()
        })
        return response.data
    }

    static async getUserProfile() {
        const response = await axios.get(`${this.BASE_URL}/users/get-logged-in-profile-info`, {
            headers: this.getHeader()
        })
        return response.data
    }


    /* This is the  to get a single user */
    static async getUser(userId) {
        const response = await axios.get(`${this.BASE_URL}/users/get-by-id/${userId}`, {
            headers: this.getHeader()
        })
        return response.data
    }

    /* This is the  to get user reservings by the user id */
    static async getUserReservings(userId) {
        const response = await axios.get(`${this.BASE_URL}/users/get-user-reservings/${userId}`, {
            headers: this.getHeader()
        })
        return response.data
    }


    /* This is to delete a user */
    static async deleteUser(userId) {
        const response = await axios.delete(`${this.BASE_URL}/users/delete/${userId}`, {
            headers: this.getHeader()
        })
        return response.data
    }

    /**ROOM */
    /* This  adds a new lab lab to the database */
    static async addLab(formData) {
        const result = await axios.post(`${this.BASE_URL}/labs/add`, formData, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return result.data;
    }

    /* This  gets all availavle labs */
    static async getAllAvailableLabs() {
        const result = await axios.get(`${this.BASE_URL}/labs/all-available-labs`)
        return result.data
    }


    /* This  gets all availavle by dates labs from the database with a given date and a lab type */
    static async getAvailableLabsByDateAndType(checkInDate, checkInTime, labType) {
        const result = await axios.get(
            `${this.BASE_URL}/rooms/available-labs-by-date-and-type?checkInDate=${checkInDate}
		&checkInTime=${checkInTime}&roomType=${labType}`
        )
        return result.data
    }

    /* This  gets all lab types from thee database */
    static async getLabTypes() {
        const response = await axios.get(`${this.BASE_URL}/labs/types`)
        return response.data
    }
    /* This  gets all labs from the database */
    static async getAllLabs() {
        const result = await axios.get(`${this.BASE_URL}/labs/all`)
        return result.data
    }
    /* This funcction gets a lab by the id */
    static async getLabById(labId) {
        const result = await axios.get(`${this.BASE_URL}/labs/lab-by-id/${labId}`)
        return result.data
    }

    /* This  deletes a lab by the Id */
    static async deleteLab(labId) {
        const result = await axios.delete(`${this.BASE_URL}/rooms/delete/${labId}`, {
            headers: this.getHeader()
        })
        return result.data
    }

    /* This updates a lab */
    static async updateLab(labId, formData) {
        const result = await axios.put(`${this.BASE_URL}/labs/update/${labId}`, formData, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return result.data;
    }


    /**RESERVING */
    /* This  saves a new reserving to the databse */
    static async reserveLab(labId, userId, reserving) {

        console.log("USER ID IS: " + userId)

        const response = await axios.post(`${this.BASE_URL}/reservings/reserve-lab/${labId}/${userId}`, reserving, {
            headers: this.getHeader()
        })
        return response.data
    }

    /* This  gets alll reservings from the database */
    static async getAllReservings() {
        const result = await axios.get(`${this.BASE_URL}/reservings/all`, {
            headers: this.getHeader()
        })
        return result.data
    }

    /* This  get resering by the cnfirmation code */
    static async getReservingByConfirmationCode(reservingCode) {
        const result = await axios.get(`${this.BASE_URL}/reservings/get-by-confirmation-code/${reservingCode}`)
        return result.data
    }

    /* This is the  to cancel user reserving */
    static async cancelReserving(reservingId) {
        const result = await axios.delete(`${this.BASE_URL}/reservings/cancel/${reservingId}`, {
            headers: this.getHeader()
        })
        return result.data
    }


    /**AUTHENTICATION CHECKER */
    static logout() {
        localStorage.removeItem('token')
        localStorage.removeItem('role')
    }

    static isAuthenticated() {
        const token = localStorage.getItem('token')
        return !!token
    }

    static isAdmin() {
        const role = localStorage.getItem('role')
        return role === 'ADMIN'
    }

    static isUser() {
        const role = localStorage.getItem('role')
        return role === 'USER'
    }
}