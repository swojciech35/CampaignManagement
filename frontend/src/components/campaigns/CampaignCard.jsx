import React from 'react';

function CampaignCard({ 
    campaign, 
    selectedSeller, 
    canEditCampaign, 
    canBidOnCampaign, 
    handleEditClick, 
    setCampaignToDelete, 
    handleBid 
}) {
    return (
        <div className="col-md-4 mb-4">
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">{campaign.name}</h5>
                    <p className="card-text">
                        <strong>Status:</strong> {campaign.status ? "Active" : "Inactive"}<br/>
                        <strong>Town:</strong> {campaign.town} +/- {campaign.radius} km<br/>
                        <strong>Keywords:</strong> {campaign.keywords?.join(', ') || 'None'}<br/>
                        <div className="d-flex justify-content-between align-items-center mt-3">
                            <div>
                                {campaign.createdBy === selectedSeller && (
                                    <span className="badge bg-info">Your Campaign</span>
                                )}
                                <span className="ms-2"><strong>Fund:</strong> {campaign.campaignFund}</span>
                            </div>
                            <div>
                                <strong>Bid Amount:</strong> {campaign.bidAmount}
                            </div>
                        </div>
                    </p>
                    {canEditCampaign(campaign) && (
                        <div className="d-flex gap-2">
                            <button
                                className="btn btn-primary"
                                onClick={() => handleEditClick(campaign)}
                            >
                                Edit
                            </button>
                            <button
                                className="btn btn-danger"
                                onClick={() => setCampaignToDelete(campaign)}
                            >
                                Delete
                            </button>
                        </div>
                    )}
                    <div className="d-flex justify-content-between mt-3">
                        {canBidOnCampaign(campaign) && (
                            <button
                                className="btn btn-primary"
                                onClick={() => handleBid(campaign.id)}
                            >
                                Bid
                            </button>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CampaignCard; 