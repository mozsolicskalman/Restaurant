using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;


namespace BookingSystem.Models
{
    public class Seller : IdentityUser
    {
        public IEnumerable<Appointment> Appointments { get; set; }

        public IEnumerable<TimeFrame> WorkingHours { get; set; }

}
}
