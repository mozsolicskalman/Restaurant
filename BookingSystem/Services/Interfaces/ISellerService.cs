using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BookingSystem.DTOs;
using BookingSystem.Data;
using BookingSystem.Models;


namespace BookingSystem.Services.Interfaces
{
    public interface ISellerService
    {
        public Task<bool> SetMeAvailableAsync(string userId, TimeFrame timeFrame);

        public Task<bool> RecieveApointmentRequest(string sellerUserId, TimeFrame timeFrame);

        public Task<bool> AceptAppointmentRequest(string sellerUserId, Appointment appointment);

        public Task<Seller> GetSellerAsync(string userId);

    }
}
