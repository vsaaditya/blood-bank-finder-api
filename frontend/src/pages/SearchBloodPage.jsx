import React, { useState } from 'react';
import { FaSearch, FaSpinner, FaTint, FaMapMarkerAlt } from 'react-icons/fa';
import { stockService } from '../services/api';

export const SearchBloodPage = () => {
  const [bloodGroup, setBloodGroup] = useState('');
  const [city, setCity] = useState('');
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [searched, setSearched] = useState(false);

  const bloodGroups = ['O+', 'O-', 'A+', 'A-', 'B+', 'B-', 'AB+', 'AB-'];

  const handleSearch = async (e) => {
    e.preventDefault();
    setError('');
    setResults([]);
    setLoading(true);
    setSearched(true);

    try {
      if (bloodGroup && city) {
        const response = await stockService.search(bloodGroup, city);
        setResults(response.data || []);
      } else {
        setError('Please select both blood group and city');
      }
    } catch (err) {
      setError('Failed to search blood availability');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 py-12 px-4">
      <div className="max-w-7xl mx-auto">
        <h1 className="text-4xl font-bold text-gray-900 mb-8 flex items-center gap-2">
          <FaTint className="text-blood-600" />
          Search Blood Availability
        </h1>

        <div className="card mb-8">
          <form onSubmit={handleSearch} className="space-y-4">
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Blood Group
                </label>
                <select
                  value={bloodGroup}
                  onChange={(e) => setBloodGroup(e.target.value)}
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blood-500 focus:border-transparent"
                  required
                >
                  <option value="">Select Blood Group</option>
                  {bloodGroups.map((group) => (
                    <option key={group} value={group}>
                      {group}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  City
                </label>
                <input
                  type="text"
                  value={city}
                  onChange={(e) => setCity(e.target.value)}
                  placeholder="Enter city name"
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blood-500 focus:border-transparent"
                  required
                />
              </div>

              <div className="flex items-end">
                <button
                  type="submit"
                  disabled={loading}
                  className="w-full btn-primary py-2 font-semibold flex items-center justify-center gap-2"
                >
                  {loading ? (
                    <>
                      <FaSpinner className="animate-spin" />
                      Searching...
                    </>
                  ) : (
                    <>
                      <FaSearch />
                      Search
                    </>
                  )}
                </button>
              </div>
            </div>
          </form>
        </div>

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">
            {error}
          </div>
        )}

        {searched && (
          <div>
            {results.length > 0 ? (
              <div className="space-y-4">
                <h2 className="text-2xl font-bold text-gray-900">
                  Found {results.length} result{results.length !== 1 ? 's' : ''}
                </h2>
                {results.map((result, index) => (
                  <div key={index} className="card">
                    <div className="flex justify-between items-start">
                      <div>
                        <h3 className="text-xl font-bold text-blood-900 mb-2">
                          {result.bankName}
                        </h3>
                        <div className="space-y-1 text-gray-600">
                          <p className="flex items-center gap-2">
                            <FaMapMarkerAlt className="text-blood-600" />
                            {result.city}, {result.state}
                          </p>
                          <p>Blood Group: <span className="font-semibold text-blood-600">{result.bloodGroup}</span></p>
                          <p>Available Units: <span className="font-semibold text-green-600">{result.availableUnits}</span></p>
                          <p>Phone: {result.contactNumber}</p>
                        </div>
                      </div>
                      <div className="bg-blood-100 rounded-lg p-4 text-center">
                        <div className="text-3xl font-bold text-blood-600">
                          {result.availableUnits}
                        </div>
                        <div className="text-sm text-gray-600">Units</div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <div className="card text-center py-12">
                <FaTint className="text-6xl text-gray-300 mx-auto mb-4" />
                <h3 className="text-2xl font-bold text-gray-900 mb-2">No Results Found</h3>
                <p className="text-gray-600">
                  Sorry, we couldn't find {bloodGroup} blood available in {city}. Please try another search.
                </p>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};
