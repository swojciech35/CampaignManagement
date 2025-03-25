import {useEffect, useState} from 'react';
import CampaignForm from './campaigns/CampaignForm';
import CampaignCard from './campaigns/CampaignCard';
import DeleteModal from './campaigns/DeleteModal';
import EditModal from './campaigns/EditModal';
import { campaignService } from '../services/campaignService';

function Campaigns() {
    const [campaigns, setCampaigns] = useState([]);
    const [sellers, setSellers] = useState([]);
    const [towns, setTowns] = useState([]);
    const [selectedSeller, setSelectedSeller] = useState(null);
    const [newCampaign, setNewCampaign] = useState({
        name: '',
        status: true,
        town: '',
        radius: 0,
        bidAmount: 0,
        campaignFund: 0,
        keywords: [],
        createdBy: ''
    });
    const [showForm, setShowForm] = useState(false);
    const [newKeyword, setNewKeyword] = useState('');
    const [campaignToDelete, setCampaignToDelete] = useState(null);
    const [campaignToEdit, setCampaignToEdit] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const { campaigns, sellers, towns } = await campaignService.fetchAllData();
                setCampaigns(campaigns);
                setSellers(sellers);
                setTowns(towns);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    const getSellerName = (sellerId) => {
        const seller = sellers.find(s => s.id === sellerId);
        return seller ? seller.name : 'Unknown Seller';
    };

    const getSellerBalance = (sellerId) => {
        const seller = sellers.find(s => s.id === sellerId);
        return seller ? seller.balance : 0;
    };

    const refreshSellers = async () => {
        try {
            const sellersData = await campaignService.refreshSellers();
            setSellers(sellersData);
        } catch (error) {
            console.error('Error refreshing sellers:', error);
        }
    };

    const handleCreateCampaign = async (e) => {
        e.preventDefault();
        if (!selectedSeller) {
            alert('Please select a seller first');
            return;
        }
        try {
            const campaignToCreate = {
                ...newCampaign,
                createdBy: selectedSeller
            };
            const campaignsData = await campaignService.createCampaign(campaignToCreate);
            setCampaigns(campaignsData);
            await refreshSellers();
            setNewCampaign({
                name: '',
                status: true,
                town: '',
                radius: 0,
                bidAmount: 0,
                campaignFund: 0,
                keywords: [],
                createdBy: ''
            });
            setShowForm(false);
        } catch (error) {
            console.error('Error creating campaign:', error);
            alert('Error creating campaign. Please check if you have enough balance.');
        }
    };

    const handleAddKeyword = () => {
        if (newKeyword.trim()) {
            setNewCampaign({
                ...newCampaign,
                keywords: [...newCampaign.keywords, newKeyword.trim()]
            });
            setNewKeyword('');
        }
    };

    const handleRemoveKeyword = (index) => {
        setNewCampaign({
            ...newCampaign,
            keywords: newCampaign.keywords.filter((_, i) => i !== index)
        });
    };

    const handleDeleteCampaign = async (campaignId) => {
        try {
            const campaignsData = await campaignService.deleteCampaign(campaignId, selectedSeller);
            setCampaigns(campaignsData);
            await refreshSellers();
            setCampaignToDelete(null);
        } catch (error) {
            console.error('Error deleting campaign:', error);
            alert('Error deleting campaign. You can only delete your own campaigns.');
        }
    };

    const handleBid = async (campaignId) => {
        try {
            const campaignsData = await campaignService.placeBid(campaignId, selectedSeller);
            setCampaigns(campaignsData);
            await refreshSellers();
        } catch (error) {
            console.error('Error placing bid:', error);
            alert(error.response?.data || 'Error placing bid. Campaign might be inactive or have insufficient funds.');
        }
    };

    const canDeleteCampaign = (campaign) => {
        return selectedSeller && campaign.createdBy === selectedSeller;
    };

    const canBidOnCampaign = (campaign) => {
        return selectedSeller && 
               campaign.status && 
               campaign.bidAmount > 0 && 
               campaign.createdBy !== selectedSeller;
    };

    const handleEditClick = async (campaign) => {
        try {
            const fullCampaign = await campaignService.fetchCampaign(campaign.id);
            setCampaignToEdit(fullCampaign);
            setNewCampaign({
                name: fullCampaign.name,
                status: fullCampaign.status,
                town: fullCampaign.town,
                radius: fullCampaign.radius,
                bidAmount: fullCampaign.bidAmount,
                campaignFund: fullCampaign.campaignFund,
                keywords: fullCampaign.keywords.map(k => k.keyword),
                createdBy: fullCampaign.createdBy
            });
        } catch (error) {
            console.error('Error fetching campaign details:', error);
            alert('Error loading campaign details for editing.');
        }
    };

    const handleUpdateCampaign = async (e) => {
        e.preventDefault();
        if (!selectedSeller) {
            alert('Please select a seller first');
            return;
        }
        try {
            const campaignToUpdate = {
                ...newCampaign,
                keywords: newCampaign.keywords.map((keyword, index) => {
                    if (campaignToEdit.keywords[index] && campaignToEdit.keywords[index].campaignKeywordsId) {
                        return {
                            campaignKeywordsId: campaignToEdit.keywords[index].campaignKeywordsId,
                            campaignId: campaignToEdit.id,
                            keyword: keyword
                        };
                    }
                    return {
                        campaignKeywordsId: null,
                        campaignId: campaignToEdit.id,
                        keyword: keyword
                    };
                }),
                updater: selectedSeller
            };
            const campaignsData = await campaignService.updateCampaign(campaignToEdit.id, campaignToUpdate);
            setCampaigns(campaignsData);
            await refreshSellers();
            setNewCampaign({
                name: '',
                status: true,
                town: '',
                radius: 0,
                bidAmount: 0,
                campaignFund: 0,
                keywords: [],
                createdBy: ''
            });
            setCampaignToEdit(null);
        } catch (error) {
            console.error('Error updating campaign:', error);
            alert('Error updating campaign. Please check if you have enough balance.');
        }
    };

    const canEditCampaign = (campaign) => {
        return selectedSeller && campaign.createdBy === selectedSeller;
    };

    return (
        <div>
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2>Campaigns</h2>
                <div className="d-flex align-items-center gap-3">
                    <div className="dropdown">
                        <button className="btn btn-outline-primary dropdown-toggle" type="button" data-bs-toggle="dropdown">
                            {selectedSeller ? getSellerName(selectedSeller) : 'Select Seller'}
                            {selectedSeller && <span className="ms-2 badge bg-primary">Balance: {getSellerBalance(selectedSeller)}</span>}
                        </button>
                        <ul className="dropdown-menu">
                            {sellers.map(seller => (
                                <li key={seller.id}>
                                    <button 
                                        className="dropdown-item" 
                                        onClick={() => {
                                        
                                            setSelectedSeller(seller.id);
                                        }}
                                    >
                                        {seller.name} (Balance: {seller.balance})
                                    </button>
                                </li>
                            ))}
                        </ul>
                    </div>
                    <button 
                        className="btn btn-primary" 
                        onClick={() => {
                            setShowForm(!showForm);
                            if (!showForm) {
                                setNewCampaign({
                                    name: '',
                                    status: true,
                                    town: '',
                                    radius: 0,
                                    bidAmount: 0,
                                    campaignFund: 0,
                                    keywords: [],
                                    createdBy: ''
                                });
                                setNewKeyword('');
                            }
                        }}
                        disabled={!selectedSeller}
                    >
                        {showForm ? 'Cancel' : 'Create New Campaign'}
                    </button>
                </div>
            </div>

            {showForm && (
                <div className="card mb-4">
                    <div className="card-body">
                        <h3>Create New Campaign</h3>
                        <CampaignForm
                            newCampaign={newCampaign}
                            setNewCampaign={setNewCampaign}
                            newKeyword={newKeyword}
                            setNewKeyword={setNewKeyword}
                            towns={towns}
                            handleAddKeyword={handleAddKeyword}
                            handleRemoveKeyword={handleRemoveKeyword}
                            onSubmit={handleCreateCampaign}
                            submitButtonText="Create Campaign"
                        />
                    </div>
                </div>
            )}

            <div className="row">
                {campaigns.map((campaign) => (
                    <CampaignCard
                        key={campaign.id}
                        campaign={campaign}
                        selectedSeller={selectedSeller}
                        canEditCampaign={canEditCampaign}
                        canBidOnCampaign={canBidOnCampaign}
                        handleEditClick={handleEditClick}
                        setCampaignToDelete={setCampaignToDelete}
                        handleBid={handleBid}
                    />
                ))}
            </div>

            <EditModal
                campaignToEdit={campaignToEdit}
                setCampaignToEdit={setCampaignToEdit}
                newCampaign={newCampaign}
                setNewCampaign={setNewCampaign}
                newKeyword={newKeyword}
                setNewKeyword={setNewKeyword}
                towns={towns}
                handleAddKeyword={handleAddKeyword}
                handleRemoveKeyword={handleRemoveKeyword}
                handleUpdateCampaign={handleUpdateCampaign}
            />

            <DeleteModal
                campaignToDelete={campaignToDelete}
                setCampaignToDelete={setCampaignToDelete}
                handleDeleteCampaign={handleDeleteCampaign}
            />
        </div>
    );
}

export default Campaigns; 