import React from 'react';

function SellerForm({ seller, setSeller, onSubmit, submitButtonText }) {
    return (
        <form onSubmit={onSubmit}>
            <div className="mb-3">
                <label className="form-label">Name</label>
                <input
                    type="text"
                    className="form-control"
                    value={seller.name}
                    onChange={(e) => setSeller({name: e.target.value})}
                    required
                />
            </div>
            <button type="submit" className="btn btn-primary">{submitButtonText}</button>
        </form>
    );
}

export default SellerForm; 