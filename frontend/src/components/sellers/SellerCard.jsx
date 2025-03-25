import React from 'react';

function SellerCard({ seller, onAddBalanceClick }) {
    return (
        <div className="col-md-4 mb-4">
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">{seller.name}</h5>
                    <p className="card-text">
                        <strong>Balance:</strong> {seller.balance}
                    </p>
                    <div className="d-flex gap-2">
                        <button
                            className="btn btn-primary"
                            onClick={() => onAddBalanceClick(seller.id)}
                        >
                            Add Balance
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default SellerCard; 