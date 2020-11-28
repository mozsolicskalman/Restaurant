using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BookingSystem.Data;
using BookingSystem.Models;
using BookingSystem.Services.Exceptions;
using BookingSystem.Services.Interfaces;
using Microsoft.AspNetCore.Identity;

namespace BookingSystem.Services.SellerService
{
    public class SellerService : ISellerService
    {
        private ApplicationDbContext _context;

        public SellerService(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<bool> SetMeAvailableAsync(string userId, TimeFrame timeFrame)
        {
            if (timeFrame.EndTime <= timeFrame.StartTime)
                throw new InvalidTimeFrameException("The start time must be before the end time.");

            Seller seller = await _context.GetSellerAsync(userId);
            seller.WorkingHours.Add(timeFrame);
            await _context.SaveChangesAsync();
            return true;
        }
        public async Task<bool> RecieveApointmentRequest(string sellerUserId, TimeFrame timeFrame)
        {
            Seller seller = await GetSellerAsync(sellerUserId);

            if (seller.IsAvailableIn(timeFrame))
            {
                return true;
            }
            else
            {
                throw new InvalidTimeFrameException("This timeframe is not bookable.");
            }
        }

        public async Task<bool> AceptAppointmentRequest(string sellerUserId, Appointment appointment)
        {
            Seller seller = await GetSellerAsync(sellerUserId);

            if (seller.IsAvailableIn(appointment.TimeFrame))
            {
                appointment.Accepted = true;
                return true;
            }
            else
            {
                throw new InvalidTimeFrameException("An other meeting was accepted instead.");
            }
        }

        public async Task<bool> AddProvidedService(string sellerUserId, Service service)
        {
            Seller seller = await GetSellerAsync(sellerUserId);

            seller.ProvidedServices.Add(service);
            return true;
        }

        public async Task<Seller> GetSellerAsync(string userId)
        {
            return await _context.GetSellerAsync(userId);
        }

        public async Task<Customer> GetCustomerAsync(string userId)
        {
            return await _context.GetCustomerAsync(userId);
        }
    }
}
