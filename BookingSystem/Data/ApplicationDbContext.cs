using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;
using BookingSystem.Models;
using System.Threading.Tasks;
using System.Linq;

namespace BookingSystem.Data
{
    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
            
        }
            public DbSet<Customer> Customers { get; set; }
            public DbSet<Seller> Sellers { get; set; }

        public async Task<Seller> GetSellerAsync(string userId)
        {
            var seller = await Sellers.FirstOrDefaultAsync(s=>s.Id.Equals(userId));
            if(seller == null)
            {
                seller = new Seller
                {
                    Id = userId,
                    Appointments = new LinkedList<Appointment>(),
                    WorkingHours = new LinkedList<TimeFrame>()
                };
                Sellers.Add(seller);
            }
            return seller;
        }
    }
}
