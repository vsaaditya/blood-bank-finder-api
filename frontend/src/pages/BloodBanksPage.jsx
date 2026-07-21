import React, { useState, useEffect } from 'react';
import { FaSpinner, FaHospital, FaMapMarkerAlt, FaPhone, FaClock } from 'react-icons/fa';
import { bloodBankService } from '../services/api';

export const BloodBanksPage = () => {
  const [banks, setBanks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [city, setCity] = useState('');
  const [filteredBanks, setFilteredBanks] = useState([]);

  useEffect(() => {
    fetchBanks();
  }, [page]);

  const fetchBanks = async () => {
    setLoading(true);
    try {
      const response = await bloodBankService.getAll(page, 10);
      setBanks(response.data.content || []);
      setFilteredBanks(response.data.content || []);
      setTotalPages(response.data.totalPages || 0);
    } catch (err) {
      setError('Failed to load blood banks');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleCityFilter = async (e) => {
    const selectedCity = e.target.value;
    setCity(selectedCity);

    if (selectedCity) {
      try {
        const response = await bloodBankService.getByCity(selectedCity);
        setFilteredBanks(response.data || []);
      } catch (err) {
        setError('Failed to filter banks');
        console.error(err);
      }
    } else {
      setFilteredBanks(banks);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <FaSpinner className="animate-spin text-blood-600 text-4xl" />
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 py-12 px-4">
      <div className="max-w-7xl mx-auto">
        <h1 className="text-4xl font-bold text-gray-900 mb-8 flex items-center gap-2">
          <FaHospital className="text-blood-600" />
          Blood Banks
        </h1>

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">
            {error}
          </div>
        )}

        <div className="card mb-8">
          <div className="flex gap-4">
            <div className="flex-1">
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Filter by City
              </label>
              <input
                type="text"
                value={city}
                onChange={handleCityFilter}
                placeholder="Enter city name"
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blood-500 focus:border-transparent"
              />
            </div>
            {city && (
              <div className="flex items-end">
                <button
                  onClick={() => {
                    setCity('');
                    setFilteredBanks(banks);
                  }}
                  className="btn-secondary px-6"
                >
                  Clear Filter
                </button>
              </div>
            )}
          </div>
        </div>

        {filteredBanks.length > 0 ? (
          <div>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
              {filteredBanks.map((bank) => (
                <div key={bank.id} className="card hover:shadow-xl transition">
                  <div className="flex items-start gap-4">
                    <div className="bg-blood-100 rounded-lg p-3">
                      <FaHospital className="text-blood-600 text-2xl" />
                    </div>
                    <div className="flex-1">
                      <h3 className="text-xl font-bold text-blood-900 mb-2">
                        {bank.name}
                      </h3>
                      <div className="space-y-2 text-sm text-gray-600">
                        <p className="flex items-center gap-2">
                          <FaMapMarkerAlt className="text-blood-600" />
                          {bank.address}, {bank.city}, {bank.state}
                        </p>
                        <p className="flex items-center gap-2">
                          <FaPhone className="text-blood-600" />
                          {bank.contactNumber}
                        </p>
                        <p className="flex items-center gap-2">
                          <FaClock className="text-blood-600" />
                          {bank.operatingHours || '24/7'}
                        </p>
                        {bank.email && (
                          <p>Email: <span className="text-blood-600 font-semibold">{bank.email}</span></p>
                        )}
                      </div>
                      {bank.isActive ? (
                        <span className="inline-block mt-3 bg-green-100 text-green-800 px-3 py-1 rounded-full text-xs font-semibold">
                          Active
                        </span>
                      ) : (
                        <span className="inline-block mt-3 bg-gray-100 text-gray-800 px-3 py-1 rounded-full text-xs font-semibold">
                          Inactive
                        </span>
                      )}
                    </div>
                  </div>
                </div>
              ))}
            </div>

            {/* Pagination */}
            <div className="flex justify-center gap-2">
              <button
                onClick={() => setPage(Math.max(0, page - 1))}
                disabled={page === 0}
                className="btn-secondary"
              >
                Previous
              </button>
              <span className="flex items-center px-4 text-gray-700 font-semibold">
                Page {page + 1} of {totalPages}
              </span>
              <button
                onClick={() => setPage(Math.min(totalPages - 1, page + 1))}
                disabled={page >= totalPages - 1}
                className="btn-secondary"
              >
                Next
              </button>
            </div>
          </div>
        ) : (
          <div className="card text-center py-12">
            <FaHospital className="text-6xl text-gray-300 mx-auto mb-4" />
            <h3 className="text-2xl font-bold text-gray-900 mb-2">No Banks Found</h3>
            <p className="text-gray-600">
              {city ? `No blood banks found in ${city}` : 'No blood banks available'}
            </p>
          </div>
        )}
      </div>
    </div>
  );
};
