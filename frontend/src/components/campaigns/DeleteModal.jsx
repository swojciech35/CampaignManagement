import React from 'react';

function DeleteModal({ campaignToDelete, setCampaignToDelete, handleDeleteCampaign }) {
    if (!campaignToDelete) return null;

    return (
        <div className="modal show d-block" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Confirm Delete</h5>
                        <button
                            type="button"
                            className="btn-close"
                            onClick={() => setCampaignToDelete(null)}
                        />
                    </div>
                    <div className="modal-body">
                        <p>Are you sure you want to delete campaign "{campaignToDelete.name}"?</p>
                    </div>
                    <div className="modal-footer">
                        <button
                            type="button"
                            className="btn btn-secondary"
                            onClick={() => setCampaignToDelete(null)}
                        >
                            Cancel
                        </button>
                        <button
                            type="button"
                            className="btn btn-danger"
                            onClick={() => handleDeleteCampaign(campaignToDelete.id)}
                        >
                            Delete
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default DeleteModal; 