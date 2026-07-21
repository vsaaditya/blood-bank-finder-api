import React, { useState, useEffect } from 'react';
import { FaDroplet, FaHospital, FaUsers, FaTint, FaSpinner } from 'react-icons/fa';
import { bloodBankService, donorService, stockService } from '../services/api';

export const DashboardPage = () => {
  const [stats, setStats] = useState({
    totalBanks: 0,
    totalDonors: 0,
    criticalStock: 0,
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const [banksRes, donorsRes, stockRes] = await Promise.all([
          bloodBankService.getAll(0, 1),
          donorService.getAll(0, 1),
          stockService.getCritical(),
        ]);

        setStats({
          totalBanks: banksRes.data.totalElements || 0,
          totalDonors: donorsRes.data.totalElements || 0,
          criticalStock: stockRes.data.length || 0,
        });
      } catch (err) {
        setError('Failed to load dashboard stats');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchStats();
  }, []);

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
        <h1 className="text-4xl font-bold text-gray-900 mb-12">Dashboard</h1>

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">
            {error}
          </div>
        )}

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
          <div className="card bg-gradient-to-br from-blood-50 to-blood-100 border-l-4 border-blood-600">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <FaHospital className="text-blood-600 text-3xl" />
              </div>
              <div className="ml-5">
                <p className="text-gray-600 text-sm font-medium">Total Blood Banks</p>
                <p className="text-3xl font-bold text-blood-900">{stats.totalBanks}</p>
              </div>
            </div>
          </div>

          <div className="card bg-gradient-to-br from-blue-50 to-blue-100 border-l-4 border-blue-600">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <FaUsers className="text-blue-600 text-3xl" />
              </div>
              <div className="ml-5">
                <p className="text-gray-600 text-sm font-medium">Total Donors</p>
                <p className="text-3xl font-bold text-blue-900">{stats.totalDonors}</p>
              </div>
            </div>
          </div>

          <div className="card bg-gradient-to-br from-orange-50 to-orange-100 border-l-4 border-orange-600">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <FaTint className="text-orange-600 text-3xl" />
              </div>
              <div className="ml-5">
                <p className="text-gray-600 text-sm font-medium">Critical Stock Items</p>
                <p className="text-3xl font-bold text-orange-900">{stats.criticalStock}</p>
              </div>
            </div>
          </div>
        </div>

        <div className="card">
          <h2 className="text-2xl font-bold text-gray-900 mb-4 flex items-center gap-2">
            <FaDroplet className="text-blood-600" />
            Quick Actions
          </h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <a href="/search" className="btn-primary text-center">
              Search Blood
            </a>
            <a href="/blood-banks" className="btn-primary text-center">
              Find Blood Banks
            </a>
            <a href="/donors" className="btn-primary text-center">
              View Donors
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};
