import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

export const campaignService = {
    // Fetch all required data
    fetchAllData: async () => {
        try {
            const [campaignsResponse, sellersResponse, townsResponse] = await Promise.all([
                axios.get(`${API_URL}/campaigns/`),
                axios.get(`${API_URL}/sellers/`),
                axios.get(`${API_URL}/common/towns`)
            ]);
            return {
                campaigns: campaignsResponse.data,
                sellers: sellersResponse.data,
                towns: townsResponse.data
            };
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    },

    // Fetch single campaign
    fetchCampaign: async (campaignId) => {
        try {
            const response = await axios.get(`${API_URL}/campaigns/${campaignId}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching campaign:', error);
            throw error;
        }
    },

    // Create new campaign
    createCampaign: async (campaignData) => {
        try {
            await axios.post(`${API_URL}/campaigns/`, campaignData);
            const response = await axios.get(`${API_URL}/campaigns/`);
            return response.data;
        } catch (error) {
            console.error('Error creating campaign:', error);
            throw error;
        }
    },

    // Update campaign
    updateCampaign: async (campaignId, campaignData) => {
        try {
            await axios.put(`${API_URL}/campaigns/${campaignId}`, campaignData);
            const response = await axios.get(`${API_URL}/campaigns/`);
            return response.data;
        } catch (error) {
            console.error('Error updating campaign:', error);
            throw error;
        }
    },

    // Delete campaign
    deleteCampaign: async (campaignId, deleterId) => {
        try {
            await axios.post(`${API_URL}/campaigns/delete`, {
                campaignId: campaignId,
                deleterId: deleterId
            });
            const response = await axios.get(`${API_URL}/campaigns/`);
            return response.data;
        } catch (error) {
            console.error('Error deleting campaign:', error);
            throw error;
        }
    },

    // Place bid on campaign
    placeBid: async (campaignId, sellerId) => {
        try {
            await axios.post(`${API_URL}/campaigns/bid`, {
                campaignId: campaignId,
                sellerId: sellerId
            });
            const response = await axios.get(`${API_URL}/campaigns/`);
            return response.data;
        } catch (error) {
            console.error('Error placing bid:', error);
            throw error;
        }
    },

    // Refresh sellers data
    refreshSellers: async () => {
        try {
            const response = await axios.get(`${API_URL}/sellers/`);
            return response.data;
        } catch (error) {
            console.error('Error refreshing sellers:', error);
            throw error;
        }
    }
}; 