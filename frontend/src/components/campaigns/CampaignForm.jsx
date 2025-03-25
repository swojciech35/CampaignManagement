import React from 'react';

function CampaignForm({ 
    newCampaign, 
    setNewCampaign, 
    newKeyword, 
    setNewKeyword, 
    towns, 
    handleAddKeyword, 
    handleRemoveKeyword, 
    onSubmit, 
    submitButtonText 
}) {
    return (
        <form onSubmit={onSubmit}>
            <div className="mb-3">
                <label className="form-label">Name</label>
                <input
                    type="text"
                    className="form-control"
                    value={newCampaign.name}
                    onChange={(e) => setNewCampaign({ ...newCampaign, name: e.target.value })}
                    required
                />
            </div>
            <div className="mb-3">
                <label className="form-label">Status</label>
                <div className="form-check form-switch">
                    <input
                        className="form-check-input"
                        type="checkbox"
                        checked={newCampaign.status}
                        onChange={(e) => setNewCampaign({ ...newCampaign, status: e.target.checked })}
                    />
                    <label className="form-check-label">
                        {newCampaign.status ? "Active" : "Inactive"}
                    </label>
                </div>
            </div>
            <div className="mb-3">
                <label className="form-label">Town</label>
                <select
                    className="form-control"
                    value={newCampaign.town}
                    onChange={(e) => setNewCampaign({ ...newCampaign, town: e.target.value })}
                    required
                >
                    <option value="">Select a town</option>
                    {towns.map((town) => (
                        <option key={town} value={town}>
                            {town}
                        </option>
                    ))}
                </select>
            </div>
            <div className="mb-3">
                <label className="form-label">Radius (km)</label>
                <input
                    type="number"
                    className="form-control"
                    value={newCampaign.radius}
                    onChange={(e) => setNewCampaign({ ...newCampaign, radius: Number(e.target.value) })}
                    required
                    min="0"
                />
            </div>
            <div className="mb-3">
                <label className="form-label">Bid Amount</label>
                <input
                    type="number"
                    className="form-control"
                    value={newCampaign.bidAmount}
                    onChange={(e) => setNewCampaign({ ...newCampaign, bidAmount: Number(e.target.value) })}
                    required
                    min="0"
                    step="0.01"
                />
            </div>
            <div className="mb-3">
                <label className="form-label">Campaign Fund</label>
                <input
                    type="number"
                    className="form-control"
                    value={newCampaign.campaignFund}
                    onChange={(e) => setNewCampaign({ ...newCampaign, campaignFund: Number(e.target.value) })}
                    required
                    min="0"
                    step="0.01"
                />
            </div>
            <div className="mb-3">
                <label className="form-label">Keywords</label>
                <div className="input-group mb-2">
                    <input
                        type="text"
                        className="form-control"
                        value={newKeyword}
                        onChange={(e) => setNewKeyword(e.target.value)}
                        placeholder="Add keyword"
                    />
                    <button type="button" className="btn btn-outline-secondary" onClick={handleAddKeyword}>
                        Add
                    </button>
                </div>
                <div className="d-flex flex-column gap-2">
                    {newCampaign.keywords.map((keyword, index) => (
                        <div key={index} className="input-group">
                            <input
                                type="text"
                                className="form-control"
                                value={keyword}
                                onChange={(e) => {
                                    const newKeywords = [...newCampaign.keywords];
                                    newKeywords[index] = e.target.value;
                                    setNewCampaign({ ...newCampaign, keywords: newKeywords });
                                }}
                            />
                            <button
                                type="button"
                                className="btn btn-outline-danger"
                                onClick={() => handleRemoveKeyword(index)}
                            >
                                Remove
                            </button>
                        </div>
                    ))}
                </div>
            </div>
            <button type="submit" className="btn btn-primary">{submitButtonText}</button>
        </form>
    );
}

export default CampaignForm; 