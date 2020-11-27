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

        public IEnumerable<Appointment> GetAppointmentsAsync(string userId)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<TimeFrame> GetWorkingHoursAsync(string userId)
        {
            throw new NotImplementedException();
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
    }
}
