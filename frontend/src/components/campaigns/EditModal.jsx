import React from 'react';
import CampaignForm from './CampaignForm';

function EditModal({ 
    campaignToEdit, 
    setCampaignToEdit, 
    newCampaign, 
    setNewCampaign, 
    newKeyword, 
    setNewKeyword, 
    towns, 
    handleAddKeyword, 
    handleRemoveKeyword, 
    handleUpdateCampaign 
}) {
    if (!campaignToEdit) return null;

    return (
        <div className="modal show d-block" tabIndex="-1">
            <div className="modal-dialog modal-lg">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Edit Campaign</h5>
                        <button
                            type="button"
                            className="btn-close"
                            onClick={() => setCampaignToEdit(null)}
                        />
                    </div>
                    <div className="modal-body">
                        <CampaignForm
                            newCampaign={newCampaign}
                            setNewCampaign={setNewCampaign}
                            newKeyword={newKeyword}
                            setNewKeyword={setNewKeyword}
                            towns={towns}
                            handleAddKeyword={handleAddKeyword}
                            handleRemoveKeyword={handleRemoveKeyword}
                            onSubmit={handleUpdateCampaign}
                            submitButtonText="Update Campaign"
                        />
                    </div>
                    <div className="modal-footer">
                        <button
                            type="button"
                            className="btn btn-secondary"
                            onClick={() => setCampaignToEdit(null)}
                        >
                            Cancel
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EditModal; 