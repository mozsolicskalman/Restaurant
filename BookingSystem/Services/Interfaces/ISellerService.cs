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

        public Task<bool> RecieveApointmentRequestAsync(string sellerUserId, TimeFrame timeFrame);

        public Task<bool> AcceptAppointmentRequestAsync(string sellerUserId, string appointmentId);

        public Task<bool> AddProvidedServiceAsync(string sellerUserId, Service service);

        public Task<Seller> GetSellerAsync(string userId);

    }
}
