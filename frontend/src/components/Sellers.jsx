import {useEffect, useState} from 'react';
import axios from 'axios';
import SellerForm from './sellers/SellerForm';
import SellerCard from './sellers/SellerCard';
import AddBalanceModal from './sellers/AddBalanceModal';

function Sellers() {
    const [sellers, setSellers] = useState([]);
    const [newSeller, setNewSeller] = useState({name: ''});
    const [selectedSeller, setSelectedSeller] = useState(null);
    const [addBalanceAmount, setAddBalanceAmount] = useState('');
    const [showAddBalanceForm, setShowAddBalanceForm] = useState(false);

    useEffect(() => {
        fetchSellers();
    }, []);

    const fetchSellers = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/sellers/');
            setSellers(response.data);
        } catch (error) {
            console.error('Error fetching sellers:', error);
        }
    };

    const handleCreateSeller = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8080/api/sellers/', newSeller);
            setNewSeller({name: ''});
            fetchSellers();
        } catch (error) {
            console.error('Error creating seller:', error);
        }
    };

    const handleAddBalance = async (e) => {
        e.preventDefault();
        try {
            await axios.put('http://localhost:8080/api/sellers/add-balance', {
                sellerId: selectedSeller,
                balanceToAdd: parseFloat(addBalanceAmount)
            });
            setAddBalanceAmount('');
            setShowAddBalanceForm(false);
            fetchSellers();
        } catch (error) {
            console.error('Error adding balance:', error);
        }
    };

    const handleAddBalanceClick = (sellerId) => {
        setSelectedSeller(sellerId);
        setShowAddBalanceForm(true);
    };

    return (
        <div>
            <h2>Sellers</h2>
            
            <div className="card mb-4">
                <div className="card-body">
                    <h3>Create New Seller</h3>
                    <SellerForm
                        seller={newSeller}
                        setSeller={setNewSeller}
                        onSubmit={handleCreateSeller}
                        submitButtonText="Create Seller"
                    />
                </div>
            </div>

            <div className="row">
                {sellers.map((seller) => (
                    <SellerCard
                        key={seller.id}
                        seller={seller}
                        onAddBalanceClick={handleAddBalanceClick}
                    />
                ))}
            </div>

            <AddBalanceModal
                show={showAddBalanceForm}
                onClose={() => setShowAddBalanceForm(false)}
                onSubmit={handleAddBalance}
                amount={addBalanceAmount}
                setAmount={setAddBalanceAmount}
            />
        </div>
    );
}

export default Sellers; 